# EventHandling

[Doc](../doc.md) > [Development](../doc.md#development) > [Event Handling](#eventhandling)

---

event handling is similar to the known system from Bukkit. Additionally it features listener registration on different level possible because of the design of atlas. by default all listeners are registered global listeners which will listen to every event regardless of its source. some events e.g. only used by server have different level. for servers you are able to register listeners directly for events only fired on a single server or his group. for custom events it is similar e.g. use a existing event group or create your own.

AtlasEvents (all events fired by atlas)

Global (listen to all events)

ProxyEvents (all events fired by a proxy)

Global (listen to all events)

Proxy (listen to events of only this proxy)

ServerEvents (all events fired by a server)

Global (listen to all events)

ServerGroup (listen to all events of server in a servergroup)

Server (listen to events only this server)

... CustomEventGroups

Global

...Custom conditions

---

additionally to the listener system with methods marked by annotations atlas features a functional interface listener.

```java
new FunctionalListenerExecutor(SomeEvent.class, (event)->{
    // your listener code here
}, ignoreCancelled, EventPriority.NORMAL);

Atlas.getPluginManager().registerFunctionListener(MY_PLUGUNG, SomeEvent.class, (event) -> {
    // your listener code here
}, ignoreCancelled, EventPriority.NORMAL);
```

---

Some modifications triggered by events may depend on being called after the event e.g. inventory modifications after event to avoid inconsistencies. this modifications are now applicable directly after the event with change in the EventPriority. EventExecutors with the priority “MONITOR” will now be called after the event is handled by the system. To replace the Bukkit EventPriority.MONITOR, PRE_MONITOR is added. in both cases PRE_MONITOR and MONITOR should not modify the event any further.
