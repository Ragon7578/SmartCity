package com.urbanlens.smartcity.repository;

import com.urbanlens.smartcity.domain.AssetDomain;
import com.urbanlens.smartcity.domain.CityAsset;
import com.urbanlens.smartcity.domain.Corridor;
import com.urbanlens.smartcity.domain.District;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory asset registry for the initialization phase.
 * Swap for PostGIS / JPA later without changing service contracts.
 */
@Repository
public class CityRepository {

    private final Map<String, District> districts = new ConcurrentHashMap<>();
    private final Map<String, Corridor> corridors = new ConcurrentHashMap<>();
    private final Map<String, CityAsset> assets = new ConcurrentHashMap<>();

    public void saveDistrict(District district) {
        districts.put(district.id(), district);
    }

    public void saveCorridor(Corridor corridor) {
        corridors.put(corridor.id(), corridor);
    }

    public void saveAsset(CityAsset asset) {
        assets.put(asset.getId(), asset);
    }

    public List<District> findAllDistricts() {
        return List.copyOf(districts.values());
    }

    public List<Corridor> findAllCorridors() {
        return List.copyOf(corridors.values());
    }

    public List<CityAsset> findAllAssets() {
        return List.copyOf(assets.values());
    }

    public List<CityAsset> findAssetsByDomain(AssetDomain domain) {
        return assets.values().stream()
                .filter(asset -> asset.getDomain() == domain)
                .toList();
    }

    public Optional<CityAsset> findAssetById(String id) {
        return Optional.ofNullable(assets.get(id));
    }

    public Collection<CityAsset> mutableAssets() {
        return assets.values();
    }

    public void clear() {
        districts.clear();
        corridors.clear();
        assets.clear();
    }

    public int assetCount() {
        return assets.size();
    }

    public List<String> snapshotIds() {
        return new ArrayList<>(assets.keySet());
    }
}
