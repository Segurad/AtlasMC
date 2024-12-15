# Unsafe API

[Doc](doc.md) > [Development](doc.md#development) > [Unsafe API](#unsafe-api)

---

API marked with __@UnsafeAPI__ and/or containing __Unsafe__ in its name

Unsafe API is mostly for performance for example by avoiding cloning of Objects.
Unless you know what you are doing don't touch it.
This API can easily create huge inconsistencies between server and client when not used correctly.
Use cases for unsafe API are for example in ChunkSections to directly change the palette or indizes without creating copies of entries. Using unsafe API for read only purpose should create no issues in most cases.
