package com.shasin.copycats.standalone.registry;

/**
 * Platform-agnostic registration hook. Implemented by the fabric and neoforge
 * modules using each loader's native registration API (DeferredRegister on
 * NeoForge, Fabric API {@code Registry.register} on Fabric).
 */
@FunctionalInterface
public interface CopycatRegistration {
    void register();
}