package de.nicolas.asset;

import com.badlogic.gdx.assets.AssetDescriptor;

/** Beschreibung für die einzelnen Assets */
public interface Asset<T> {

    AssetDescriptor<T> getDescriptor();
}
