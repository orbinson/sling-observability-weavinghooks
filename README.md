# Sling Observability Weaving Hooks

Library of weaving hooks to ease the process of observability in out-of-the-box code

## Log Method Weaving Hook

When all else fails, and you have no logs available because there are no log statements in the out-of-the-box classes, use this Log Method Weaving Hook to add a dynamic log statements

### Usage

Install the bundle in start level 1 and add an OSGi config for every method log you would like to add.

Example, search for your classname and method name you want to log and add an OSGi config `be.orbinson.sling.observability.weavinghooks.logmethod.LogMethodWeavingHookConfiguration~MyClass-doGet.cfg.json`

```json
{
  "className": "my.package.MyClass",
  "methodName": "doGet"
}
```

Optionally you can also set the `logLevel` (default is INFO) and that it logs the generated bytecode of the weaved class by setting the `showGeneratedBytecode` through the OSGi config

To make the weaving hook work, a refresh of the target bundle is required.

## Future

- Add weaving hooks to create custom spans and metrics using OpenTelemetry
