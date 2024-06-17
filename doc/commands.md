# Commands

Atlas has a command API verry similar to mojangs bridadier.
As Developer you have the powerfull option define your commands with simple YAML. There is no need to pares and verify commands yourself. You just need to implement the CommandExecutor that will handle your Command

See also:
- [VarArgParser](var_arg_parser.md) for a list all default parsers
- [SuggestionTypes](suggestion_types.md) for a list of all default suggestion types

Example Command configuraon:

```yaml  
commands:
- name: Foo # <required> Name of the command
  aliases: # <optional> defines aliaes used within you command this is only applicable on the root element and literals
  - Bar
  # A general description of the purpose of this command
  # Used ti generate help command list
  cmd-description: "Some description"
  # A more detailed description of the usage if executed at this point
  # Used to generate help this command
  description: "Some description"
  tempaltes:
  - template-name: MyTemplate
    # same definitions as argument
    # if a templates uses tempalte: <name> within its arguments the theplate must be defined before this template
  args:
  - template: MyTemplate # using a template as deined in the root as argument
  - type: literal # <required> defines literal arguments for your command. literals have the same definition as the root element
    name: FooArg
    permission: some.arg.permission
    executor: myplugin:executor
    arg-key: someKey # <optional> string. defines a key that will be mapped with the name of this literal and supplied as argument to the executor
    aliases:
    - FA
    #...
  - type: var # <optional> defines variable arguments that parse user input used for your executor. var args have the same definiton as the root plus some extras
    name: FooVarArg # <required> this name is provided for the executor as key to access the parsed value
    # <required> String | Object 
    # Defines how this argument is parsed
    # Object form is used when the parser needs some configurations otherwise a string is all you need
    # parsers are defined in the ClassRegistry(atlas:command/var_arg_parser) 
    # parser: some_parser:key
    parser:
      # <required> registry entry key
      type: brigadier:integer
      # defined by brigadier:integer parser see doc
      min: 0
      max: 5 
    # <optional> String | Object
    # Defines if this argument provides any suggestion for the user
    # Obejct for is used when the suggestion type need some configuration otherwise 
    suggestion: 
      type: some_suggestion:key
      # additional configuration goes here
      # suggestion: some_suggestion:key
  executor: some_executor:key # <optional> defines the executor used when command ends at this node
  allowed-source: ANY # <optional> ANY|PLAYER|CONSOLE defines who can invoke this node defaults to ANY
  permission: some.permission.to.execute # <optinal> defines a permission that is required to execute this node
#...
```

### Dummy Executor

Atlas provides a non functional executor called ```atlas:dummy_executor``` as placeholder.
This executor does echo the command sender his used command.
It can be used for defining command and implementing the required executors later.
