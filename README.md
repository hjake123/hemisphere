# Hemisphere
This is a library mod made for use in commissions to integrate GeckoLib animations with Walkers, among other repeated tasks.

# Setup
This mod can be built using gradle, and is intended to be used with the JarJar dependency system.
To get started, clone the repo and build using the `publishToMavenLocal` task. In your mod, include this line in your build.gradle:

Then, in your `build.gradle`, add these lines:
```
repositories {
...
    mavenLocal()
...
}

dependencies {
...
    implementation fg.deobf(jarJar(group: 'dev.hyperlynx', name: 'hemisphere', version: '[1.0,2.0)'))
...
}
```

The version property may have to be updated depending on the version of Hemisphere you're using. The version number follows Semantic Versioning, so no breaking changes will be done without changing the major version number.

# Uses

## Walkers / Remorphed
To add an animation for a custom entity to use while a player is morphed into it via Walkers/Remorphed, follow these steps:

1. Create a deferred register for your animations, like this:
```java
public static final DeferredRegister<MorphAnimation<?>> MORPH_ANIMATIONS = MorphAnimations.makeDeferredRegister("your_modid");
```

2. Register a MorphAnimation<YourEntity>, where YourEntity is the entity you're animating:
```java
public static final RegistryObject<MorphAnimation<YourEntity>> PUNCH = MORPH_ANIMATIONS.register("punch", () ->
            new MorphAnimation<>(
                    12, // How many ticks does it take for the animation to complete?
                    (mob) -> mob.setPunching(true), // A lambda to run to trigger the animation to start
                    (mob) -> mob.setPunching(false) // A lambda to run to trigger the animation to end
            ));
```

3. Set up your entity to update and check an EntityDataAccessor to decide its animation state. These DO exist on entities a player is "controlling" while morphed into them:
```java
    protected static final RawAnimation PUNCH_ANIM = RawAnimation.begin().thenPlay("attack");

    protected static final EntityDataAccessor<Boolean> PUNCHING = SynchedEntityData.defineId(ExampleEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PUNCHING, false);
        // Define any other synched data as well
    }

    public boolean isPunching() {
        return this.getEntityData().get(PUNCHING);
    }

    public void setPunching(boolean attacking) {
        this.getEntityData().set(PUNCHING, attacking);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Attack", 1, this::attackAnimationController));
        // Add other controllers like walking as well
    }

    private PlayState attackAnimationController(AnimationState<ExampleEntity> state) {
        if (this.isPunching()) {
            return state.setAndContinue(PUNCH_ANIM);
        }
        state.resetCurrentAnimation();
        return PlayState.STOP;
    }
```

4. Register your punch and right click animations during FMLCommonSetup
Each mob may have one punch and one use (right click) animation assigned to it. This can be done at any time, but to avoid confusion I'd recommend doing during your FMLCommonSetup event handler.
```java
MorphAnimations.registerPunchAnimation(ModEntityTypes.YOUR_ENTITY_TYPE.get(), PUNCH.getId());
MorphAnimations.registerUseAnimation(ModEntityTypes.YOUR_ENTITY_TYPE.get(), USE.getId());
```

You can also play morph attack animations directly using `MorphAttackAnimations::playAttackAnim`, like so:
```java
MorphAnimationController.playAttackAnim(player.getUUID(), ModMorphFeatures.OTHER_ATTACK.getId());
```

## Keybindings
Hemisphere also adds a simple way to add and check keybindings. You mostly just have to create KeyBinding instances, call their add function, and add one event handler. Here's an example:
```java
@Mod.EventBusSubscriber
public class ModKeyBindings {
    static {
        new KeyBinding(
                Lazy.of(() -> new KeyMapping(
                        "key.yourmod.shoot", // Translation key for this entry in the controls menu
                        KeyConflictContext.IN_GAME,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_B,
                        "key.categories.yourmod.feature" // Translation key for the category that your key goes into
                )),
                () -> {
                    // This block is run on the client side each time the key is pressed.
                    // You may want to send a message to the server to affect the state of the game.
                    YourMod.CHANNEL.sendToServer(new ShootMessage());
                }
        ).add(YourMod.MODID);
    }

    @SubscribeEvent
    public static void registerKeyMaps(RegisterKeyMappingsEvent event) {
        KeyBinding.registerKeyMappings(event, YourMod.MODID);
    }
}
```

Remember, you **MUST** use `Lazy.of` here! It will not work if you don't!

## /role_skin
Hemisphere adds a new command called `/role_skin` which allows players to transform into either creatures (with Walkers) or other skins (with Re:Skin).
To use it, you just need to register the URL for the skin or the entity type for the morph in common setup, like so:
```java
    RoleSkins.registerSkin("SkinFromImgur", "https://i.imgur.com/?????.png");
    RoleSkins.registerSkin("Entity", ModEntityTypes.YOUR_ENTITY.get());
```

Note that URL skins will only work if Re:Skin is available, and entity skins will only work if Walkers is available.

## Other
There are two other classes included under the `util` package.
`HyperMobEffect` is just MobEffect, but with an accessible constructor.
`ParticleScribe` is a way to draw patterns of particles, like spheres, circles, and lines, which works on both the client and server side. Feel free to call whichever of its functions you want.

If you have any questions, please get in touch!
Discord: `hyperlynx`
