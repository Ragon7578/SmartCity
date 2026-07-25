package com.urbanlens.smartcity.service;

import com.urbanlens.smartcity.domain.AssetDomain;
import com.urbanlens.smartcity.domain.CityAsset;
import com.urbanlens.smartcity.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Canonical catalog of managed city things.
 */
@Service
public class AssetRegistryService {

    private final CityRepository cityRepository;

    public AssetRegistryService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityAsset> listAssets() {
        return cityRepository.findAllAssets();
    }

    public List<CityAsset> listAssets(AssetDomain domain) {
        if (domain == null) {
            return listAssets();
        }
        return cityRepository.findAssetsByDomain(domain);
    }

    public Optional<CityAsset> getAsset(String id) {
        return cityRepository.findAssetById(id);
    }

    public int count() {
        return cityRepository.assetCount();
    }
}
