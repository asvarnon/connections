# InfoCommand Bot

The `InfoCommand` is a highly configurable and dynamic command in our bot, allowing users to fetch information about various entities like `players` and `artisans` with different criteria.

## Overview

The `InfoCommand` works by interpreting user inputs and executing specific subcommands or dynamic handlers to fetch the required information. The bot provides a default behavior for registered static commands and also handles dynamic queries for more flexibility.

### Basic Usage

The command can be invoked using the following syntax:

- **entity**: The type of information to retrieve (e.g., `player`, `artisan`).
- **criteria**: Additional filtering or specification for the desired result (optional).

---

## Current Commands

Here is a list of currently supported commands:

### Static Commands
These commands return all available information for a specific entity.

- `!info player`: Lists all registered players.
- `!info artisan`: Lists all available artisans.

### Dynamic Commands
These commands include criteria to narrow down the search for more specific results.

- **Player Commands**:
    - `!info player <archetype>`: Fetches players by their archetype (e.g., cleric, ranger).
    - `!info player <name>`: Fetches detailed information about a player by name.
- **Artisan Commands**:
    - `!info artisan <artisan-type>`: Fetches all artisans of a specific artisan type (e.g., blacksmith, alchemist).

---

## Features

Here are the updated features of the `InfoCommand`:

1. **Static Subcommands for Default Listings**
    - Supported entities have predefined static subcommands to list all relevant information.
    - Example:
        - `!info player`: Lists all players.
        - `!info artisan`: Lists all artisans.

2. **Dynamic Criteria Handling**
    - The bot supports dynamic queries for more specific lookups.
    - Dynamic queries rely on a `DynamicValueService` to fetch or resolve dynamic values based on the provided criteria.
    - Example:
        - `!info player <archetype>`: Fetches players by their archetype.
        - `!info player <name>`: Fetches a player by name.
        - `!info artisan <artisan-type>`: Fetches artisans by type.

3. **Error Handling**
    - Graceful error messages when:
        - The user provides insufficient or incorrect input.
        - Unsupported dynamic criteria are provided.

4. **Dynamic Value Types**
   The `DynamicValueService` processes multiple types of dynamic values including:
    - `archetype`: Filters players by their archetype (e.g., healer, DPS).
    - `name`: Filters players or artisans by their name.
    - `artisan-type`: Filters artisans by type (e.g., blacksmith, alchemist).

5. **Fallback for Unknown Entities**
    - If the entity is not recognized, the command provides an error message (`Unknown entity`).
    - If specific criteria are invalid, a user-friendly error is displayed.

---

### Examples

1. **Basic List-All Commands**
    - `!info player`: Shows all registered players.
    - `!info artisan`: Shows all available artisans.

2. **Dynamic Queries**
    - `!info player healer`: Fetches all players categorized as `healer`.
    - `!info player JohnDoe`: Fetches the player named `JohnDoe`.
    - `!info artisan blacksmith`: Fetches all artisans belonging to the `blacksmith` artisan type.

3. **Empty or Invalid Input**
    - `!info`: Displays the usage guide (`❌ Usage: !info <entity> [criteria]`).
    - `!info unknownentity`: Displays an error (`❌ Unknown entity: 'unknownentity'.`).
    - `!info artisan invalidtype`: Displays an error (`❌ Invalid artisan lookup for 'invalidtype'. Check input.`).

---

## Command Workflow

1. The command parses the input to extract the `entity` and optional `criteria`.
2. It checks if the `entity` has a static subcommand for a list-all operation (e.g., all players or artisans).
3. If no static subcommand exists, it attempts to resolve the `entity` and `criteria` dynamically using the `DynamicValueService`.
4. Based on the type of criteria (`archetype`, `name`, `artisan-type`), specific handler functions are invoked to fetch and display results.
5. If no valid handler or dynamic value is found, fallback logic or error messages are triggered.

---

## Setting Up the Command

### Adding New Static Subcommands

To add new list-all functionality for additional entities:
1. Register the `SubCommand` in the `registerStaticSubCommands()` method.
2. Provide an implementation for the `SubCommand` in the respective handler.

Example:

### Extending Dynamic Criteria

To expand the range of supported dynamic values:
1. Add new types to the `DynamicValue` entity.
2. Handle the new types in the `processDynamicValue()` method.

Example:

### Error Customization

Modify fallback or error messages in the various methods to tailor the bot's responses to your preferences.

---

## Dependencies

- **JDA (Java Discord API)**: For interacting with Discord servers.
- **Spring Framework**: For dependency injection and service management.
- **Lombok (Optional)**: For reducing boilerplate in code.

---

## Future Improvements

- Expand support for additional entities and criteria.
- Add configurable responses for dynamic queries.
- Add caching for frequently queried dynamic values.

For contributions or issues, feel free to open a pull request or create an issue in the repository.