# Superhero Arena: Interface Battle Lab

## Mission

You are creating one superhero, antihero, or supervillain for the class arena.

You will **not** write the battle engine. Your job is to create one Java class that describes what your character can do. The arena will read your class through interfaces and decide what actually happens in battle.

This means your character can say:

> "I want to use Thunder Jab with 45 damage and 70 accuracy."

But the arena decides:

> "Did it hit? How much damage actually happened? Did the defender dodge?"

## Why this matters

This lab is about object design. Your hero will implement several small interfaces. Each interface describes one capability:

```text
                 ArenaHero
                    ^
                    |
              YourHeroClass
              /     |      \
        CanMove  HasPower  HasMorality
```

Your class is one object, but it can be treated as several interface types by the arena.

## Files you are given

Your starter project includes these important files:

| File | Purpose |
|---|---|
| `ArenaHero.java` | Basic information every contestant must provide |
| `CanMove.java` | Interface for movement |
| `HasPower.java` | Interface for special powers |
| `HasMorality.java` | Interface for hero/villain behavior |
| `MoveContext.java` | Information your hero receives when deciding where to move |
| `Movement.java` | Object returned by `move()` |
| `PowerMove.java` | Object returned by `usePower()` |
| `HeroRules.java` | Arena limits and build-budget rules |
| `StarterHero.java` | The class you will customize |
| `TrainingRoom.java` | Run this to test your hero |
| `PracticeVillain.java` | A simple opponent for local testing |

## Your task

Create one class that implements all four required interfaces:

```java
public class StarterHero implements ArenaHero, CanMove, HasPower, HasMorality {
    // your code here
}
```

You may keep the class named `StarterHero` while you are practicing. Before submitting, your instructor may ask you to rename it to something unique, such as:

```java
public class MayaLightningHero implements ArenaHero, CanMove, HasPower, HasMorality {
    // your code here
}
```

The file name must match the class name.

## Required methods

### `ArenaHero`

```java
String getName();
String getCatchphrase();
int getMaxHealth();
```

Example:

```java
@Override
public String getName() {
    return "Captain Comet";
}

@Override
public String getCatchphrase() {
    return "Gravity is only a suggestion!";
}

@Override
public int getMaxHealth() {
    return 105;
}
```

### `CanMove`

```java
Movement move(MoveContext context);
```

Example:

```java
@Override
public Movement move(MoveContext context) {
    if (context.getMyHealth() < 40) {
        return Movement.awayFromOpponent(context, "backs away to recover");
    }

    if (context.getDistanceToOpponent() > 5) {
        return Movement.towardOpponent(context, "rockets across the arena");
    }

    return Movement.stay("holds position and waits for an opening");
}
```

The arena is a `20 x 20` grid. Your hero chooses an x/y movement request each
turn. The arena keeps the move inside the arena and limits the distance.

Useful `MoveContext` methods include:

| Method | Meaning |
|---|---|
| `getMyX()` / `getMyY()` | Your current position |
| `getOpponentX()` / `getOpponentY()` | The opponent's current position |
| `getDeltaXToOpponent()` | How far left/right the opponent is |
| `getDeltaYToOpponent()` | How far up/down the opponent is |
| `getDistanceToOpponent()` | Straight-line distance to the opponent |
| `getMyHealth()` / `getOpponentHealth()` | Current health values |
| `isNearWall()` | Whether you are close to an arena wall |

Beginner movement can move toward or away from the opponent. More advanced
movement can use the x/y deltas to circle or side-step.

### `HasPower`

```java
PowerMove usePower();
```

Example:

```java
@Override
public PowerMove usePower() {
    return new PowerMove("Comet Punch", 48, 74);
}
```

The power numbers mean:

| Value | Meaning |
|---|---|
| `damage` | How hard the power can hit |
| `accuracy` | How likely the power is to hit |

### `HasMorality`

```java
int getEvilness();
String getMotto();
```

Example:

```java
@Override
public int getEvilness() {
    return 20;
}

@Override
public String getMotto() {
    return "Save the city first. Win the fight second.";
}
```

Evilness is from `0` to `100`:

| Evilness | Meaning |
|---:|---|
| `0` | Very heroic |
| `50` | Neutral or antihero |
| `100` | Very villainous |

The arena may treat heroic, neutral, and villainous characters a little differently. For example, a very heroic character may show mercy. A very villainous character may hit harder when losing, but may also become less accurate.

## Arena limits

The arena will clamp values that are too high or too low.

| Stat | Minimum | Maximum |
|---|---:|---:|
| Health | 80 | 140 |
| Damage | 10 | 80 |
| Accuracy | 40 | 95 |
| Evilness | 0 | 100 |

The arena is `20 x 20`, and the maximum movement distance per turn is `4`.
If your hero requests a bigger move, the arena will shorten it.

So this does not work the way you might hope:

```java
return new PowerMove("Destroy Universe", 999999, 999999);
```

The arena will reduce those numbers to legal values.

## Build budget

Your hero also has a build budget.

```text
health + damage + accuracy <= 260
```

Example legal build:

```text
Health: 110
Damage: 45
Accuracy: 70

Cost = 110 + 45 + 70
Cost = 225
```

That build is legal because `225 <= 260`.

Example over-budget build:

```text
Health: 140
Damage: 80
Accuracy: 95

Cost = 140 + 80 + 95
Cost = 315
```

That build is over budget. The tournament arena will apply a penalty.

## Rules

Do not edit the interface files.

Do not edit `HeroRules.java` for your submitted hero.

Your hero should not use:

```text
System.exit
Scanner
File I/O
threads
reflection
infinite loops
Random or Math.random
```

The arena already handles randomness. Your methods should return your character's normal stats and descriptions.

## Step-by-step

### Step 1: Run the starter project

Open the starter project and run:

```text
TrainingRoom.java
```

You should see a hero build report and a practice duel.

### Step 2: Customize the hero identity

In `StarterHero.java`, update:

```java
getName()
getCatchphrase()
getMotto()
getEvilness()
```

Make the character yours.

### Step 3: Customize movement

Update `move(MoveContext context)`:

```java
@Override
public Movement move(MoveContext context) {
    if (context.getDistanceToOpponent() > 5) {
        return Movement.towardOpponent(context, "vanishes into a burst of blue sparks");
    }

    return Movement.by(0, 3, "side-steps in a burst of blue sparks");
}
```

### Step 4: Customize the power

Update `usePower()`:

```java
@Override
public PowerMove usePower() {
    return new PowerMove("Blue Spark Uppercut", 50, 68);
}
```

### Step 5: Check the build report

Run `TrainingRoom.java` again.

Look for:

```text
Status: LEGAL BUILD
```

If you are over budget, reduce health, damage, or accuracy.

### Step 6: Run practice battles

Run the training room a few times. Because the battle has randomness, the same hero will not always win.

### Step 7: Submit

Submit the `.java` file for your hero, following your instructor's directions.

Your hero class must compile and must implement:

```text
ArenaHero
CanMove
HasPower
HasMorality
```

## Final checklist

Before submitting, confirm:

- Your project compiles.
- `TrainingRoom.java` runs.
- Your build report says `LEGAL BUILD`.
- Your hero has a name, catchphrase, movement, power, evilness, and motto.
- Your hero class implements all four required interfaces.
- You did not modify the arena rules to make your hero stronger.
