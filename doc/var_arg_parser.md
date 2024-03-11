# VarArgParser

- [brigadier:bool](#brigadierbool)
- [brigadier:float](#brigadierfloat)
- [brigadier:double](#brigadierdouble)
- [brigadier:integer](#brigadierinteger)
- [brigadier:long](#brigadierlong)
- [brigadier:string](#brigadierstring)
- [minecraft:entity](#minecraftentity)
- [minecraft:block_pos](#minecraftblock_pos)
- [minecraft:column_pos](#minecraftcolumn_pos)
- [minecraft:vec3](#minecraftvec3)
- [minecraft:vec2](#minecraftvec2)

## brigadier:bool

## brigadier:float

```yaml
type: brigadier:float
min: 0.0 # <optional> int min value default Float.MIN_VALUE
max: 3.5 # <optional> in max value default Float.MAX_VALUE
```

## brigadier:double

```yaml
type: brigadier:double
min: 0.0 # <optional> int min value default Double.MIN_VALUE
max: 3.5 # <optional> in max value default Double.MAX_VALUE
```

## brigadier:integer

```yaml
type: brigadier:integer
min: 0 # <optional> int min value default Integer.MIN_VALUE
max: 10 # <optional> in max value default Integer.MAX_VALUE
```

## brigadier:long

```yaml
type: brigadier:long
min: 0 # <optional> int min value default Long.MIN_VALUE
max: 10 # <optional> in max value default Long.MAX_VALUE
```

## brigadier:string

```yaml
type: brigadier:string
# <optional> string
# default: SINGLE_WORD
# SINGLE_WORD = single unquoted word
# QUOTABLE_PHRASE = reads a "quoted string"
# GREEDY_PHRASE = reads the input string until the end
string-type: SINGLE_WORD # SINGLE_WORD | QUOTABLE_PHRASE | GREEDY_PHRASE
```
## minecraft:entity

```yaml
type: minecraft:entity
# <optional> boolean
# default: false
# if true allow ony player names as input otherwise UUIDs of entites are alloed
player-only: true
# <optional> boolean
# default: false
# if true only allows a single entity/player
signle-entity: true 
```

## minecraft:block_pos

## minecraft:column_pos

## minecraft:vec3

## minecraft:vec2