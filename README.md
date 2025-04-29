# connections

GENERAL:

This is a general workspace for any testing of data structures, implementations, documentation, etc to learn from while building out basic tools

# InfoCommand

The `InfoCommand` is an integral part of the Connections project, handling user commands related to retrieving information about **players** and **artisans**. This command supports dynamic queries and modular structure, enabling easy access to data and effortless scalability for new features.

---

## **Usage**

The `InfoCommand` processes user commands starting with `!info` followed by specific **entities** (`player` or `artisan`) and optional **subcommands** or **filters**.

### **Basic Command Syntax**

### **Examples:**

#### **Player Queries:**
- `!info player`: Lists all players along with their archetypes.
- `!info player full`: Lists all players and their associated artisans.
- `!info player name John Doe`: Retrieves details about the player **John Doe**.
- `!info player archetype Cleric`: Lists players with the archetype **Cleric**.

#### **Artisan Queries:**
- `!info artisan`: Lists all artisans.
- `!info artisan type Crafting`: Retrieves artisans categorized under the type **Crafting**.
- `!info artisan Carpentry`: A dynamic query to find artisans specializing in **Carpentry**.

---

## **For Developers**

The `InfoCommand` is designed with modularity and maintainability in mind. Its architecture is broken into two primary components:

1. **Handlers**:
    - `PlayerInfoHandler`: Handles all player-related operations.
    - `ArtisanInfoHandler`: Handles all artisan-related operations.

2. **SubCommand Registry**:
    - A flexible map of subcommands (`subCommandRegistry`) drives the dispatching of actions based on user input.
    - Each subcommand is mapped to a dedicated handler method or lambda expression for processing.

---

### **Code Structure**

- **Handlers**:  
  Located in `com.home.connections.command.handlers`, the `PlayerInfoHandler` and `ArtisanInfoHandler` encapsulate all business logic for their respective domains.

    - **Methods in Handlers:**
        - **PlayerInfoHandler**:
            - `handleListAll`: List all players.
            - `handleByArchetype`: Filter players by archetype.
            - `handleWithArtisans`: Show players associated with artisans.
            - `handleByName`: Retrieve details about a specific player by name.
        - **ArtisanInfoHandler**:
            - `handleListAll`: List all artisans.
            - `handleByType`: Filter artisans by type.
            - `handleDynamicQuery`: Perform a dynamic lookup based on user input.

- **Command Execution**:  
  The entry point is `InfoCommand`, which handles message parsing and delegates subcommands dynamically to the appropriate handler based on user input.

---

### **Processing User Commands**

1. **SubCommand Registry:**
    - The `registerSubCommands` method populates the `subCommandRegistry` with supported commands and their corresponding actions.
    - Example:
      ```java
      subCommandRegistry.put("player name", (args, event) -> playerInfoHandler.handleByName(String.join(" ", args), event));
      subCommandRegistry.put("artisan type", (args, event) -> {
          if (args.isEmpty()) {
              event.getChannel().sendMessage("❌ Please provide an artisan type.").queue();
          } else {
              artisanInfoHandler.handleByType(args.get(0), event);
          }
      });
      ```

2. **Dynamic Command Parsing:**
    - The `execute` method splits the user input:
        - Identifies the subcommand and filters.
        - Finds the appropriate handler in the registry.
        - Handles invalid commands gracefully.

3. **Handler Methods**:
    - The handlers provide specialized logic for processing queries and formatting responses. They directly interact with the `PlayerService` and `ArtisanService` to fetch necessary data.

---

### **Adding New SubCommands**

To extend the functionality:
1. Add a handler method in the appropriate handler class (e.g., `PlayerInfoHandler` or `ArtisanInfoHandler`).
2. Register the new subcommand in the `subCommandRegistry` located in `InfoCommand`.

Example:

---

## **Features**

- **Dynamic Query Handling**: Parse and handle flexible queries dynamically, such as `!info artisan Carpentry`.
- **Error Feedback**: Gracefully handles invalid subcommands or missing filters with appropriate messages.
- **Modular Design**: Clear separation of player and artisan concerns into `PlayerInfoHandler` and `ArtisanInfoHandler`.
- **Scalable Architecture**: Supports easy addition of new subcommands.

---

## **Key Classes and Methods**

1. **InfoCommand.java**
    - `execute`: Parses user commands and delegates to the correct subcommand handler.
    - `registerSubCommands`: Maps commands to their handlers using a registry.

2. **PlayerInfoHandler.java**
    - Core logic for player-related queries such as `name`, `archetype`, and `full`.

3. **ArtisanInfoHandler.java**
    - Core logic for artisan-related queries such as `type` or dynamic artisan lookups.

---

## **Supported Entities & Commands**

| **Entity** | **Subcommand**             | **Description**                                                                 |
|------------|----------------------------|---------------------------------------------------------------------------------|
| `player`   |                            | Lists all players.                                                             |
|            | `full`                     | Lists all players and their associated artisans.                               |
|            | `name {name}`              | Retrieves details of the specified player by name.                             |
|            | `archetype {name}`         | Retrieves a list of players with the specified archetype.                      |
| `artisan`  |                            | Lists all artisans.                                                            |
|            | `type {type}`              | Lists all artisans of a specified type (e.g., `Crafting`).                     |
|            | `{dynamic query}`          | Performs a dynamic lookup for artisans based on the user’s input (e.g., Carpentry). |

---

## **Error Handling**

The `InfoCommand` and its handlers provide detailed error feedback for common issues:
- **Missing Arguments:** Example: `!info artisan type` with no type provided.
- **Unknown Commands:** Example: Subcommands like `!info player skill` not registered.
- **Empty Results:** Example: Query returns no matching players or artisans.

---

## **Future Improvements**

- Support for additional filters (e.g., artisan level queries, player guilds).
- Add paginated responses for large datasets (e.g., when lists exceed message limits).

---

By delegating logic to `PlayerInfoHandler` and `ArtisanInfoHandler`, `InfoCommand` is designed for **extensibility**, **clarity**, and **ease of testing**. Developers can confidently extend or maintain features with minimal risk of introducing bugs.
