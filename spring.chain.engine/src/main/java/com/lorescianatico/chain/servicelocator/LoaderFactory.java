package com.lorescianatico.chain.servicelocator;

import com.lorescianatico.chain.loader.ChainLoader;
import com.lorescianatico.chain.util.ConfigType;

public interface LoaderFactory {

    ChainLoader getLoader(ConfigType configType);

}
