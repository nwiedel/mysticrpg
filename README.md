# MysticRPG

The Project is completely written after the youtube tutorial from

Quilraven: LibGDX & Tiled RPG Tutorial

![LibGDX](https://img.shields.io/badge/LibGDX-1.13.5-green)
![Ashley](https://img.shields.io/badge/Ashley-1.7.4-blue)
![Tiled](https://img.shields.io/badge/Tiled-1.11-red)

This is the source code for the LibGDX Java [tutorial](https://www.youtube.com/playlist?list=PLTKHCDn5RKK8us8DL7OGqgp4rQQByiX0C) series on my
[YouTube](https://www.youtube.com/Quillraven) channel.

The game itself contains:
- main menu using Scene2D
- Input multiplexer (keyboard + scene2d stage)
- playable character
- Tiled integration using an objects tileset for game objects
- Box2D integration for collision handling
- a trap trigger
- a heart lifebar including life regeneration
- an entity component system architecture using Ashley (ECS)

### Controls

- WASD: Movement + UI navigation
- SPACE: Attack + UI select
- ESCAPE: Change screen from game to menu

### Credits
- [Cute Fantasy assets](https://kenmi-art.itch.io/cute-fantasy-rpg)
- [JRPG Music](https://yubatake.bandcamp.com/album/jrpg-collection)
- [UI essentials](https://crusenho.itch.io/complete-ui-essential-pack)
- [UI pack forest](https://toffeecraft.itch.io/ui-user-interface-forest)
- [8-bit-fantasy Music](https://xdeviruchi.itch.io/8-bit-fantasy-adventure-music-pack)
- [SFX generator](https://sfxr.me/)
- [Training dummy](https://elthen.itch.io/2d-pixel-art-training-dummy)




A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
