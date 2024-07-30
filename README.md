# Sling Observability Weaving Hooks

Library of weaving hooks to ease the process of observability in out-of-the-box code

## Log Method Weaving Hook

When all else fails, and you have no logs available because there are no log statements in the out-of-the-box classes, use this Log Method Weaving Hook to add a dynamic log statements

### Usage

Install the bundle in start level 1 and add an OSGi config for every method log you would like to add.

Example, search for your classname, method name and amount of parameters you want to log and add an OSGi config `be.orbinson.sling.observability.weavinghooks.logmethod.LogMethodWeavingHookConfiguration~MyClass-doGet.cfg.json`

```json
{
  "className": "my.package.MyClass",
  "methodName": "doGet",
  "amountOfParameters": 2
}
```

To make the weaving hook work, either a framework restart of an entire java process restart is required.

## Future

- Add weaving hooks to create custom spans and metrics using OpenTelemetry