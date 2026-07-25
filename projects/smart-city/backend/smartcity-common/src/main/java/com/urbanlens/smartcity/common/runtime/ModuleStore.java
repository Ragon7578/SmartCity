package com.urbanlens.smartcity.common.runtime;

import com.urbanlens.smartcity.common.dto.CorridorDto;
import com.urbanlens.smartcity.common.dto.DistrictDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleStore {

    private final Map<String, DistrictDto> districts = new ConcurrentHashMap<>();
    private final Map<String, CorridorDto> corridors = new ConcurrentHashMap<>();
    private final Map<String, ManagedAsset> assets = new ConcurrentHashMap<>();

    public void saveDistrict(DistrictDto district) {
        districts.put(district.id(), district);
    }

    public void saveCorridor(CorridorDto corridor) {
        corridors.put(corridor.id(), corridor);
    }

    public void saveAsset(ManagedAsset asset) {
        assets.put(asset.getId(), asset);
    }

    public List<DistrictDto> districts() {
        return List.copyOf(districts.values());
    }

    public List<CorridorDto> corridors() {
        return List.copyOf(corridors.values());
    }

    public List<ManagedAsset> assets() {
        return List.copyOf(assets.values());
    }

    public Collection<ManagedAsset> mutableAssets() {
        return assets.values();
    }

    public Optional<ManagedAsset> findAsset(String id) {
        return Optional.ofNullable(assets.get(id));
    }

    public int assetCount() {
        return assets.size();
    }

    public void clear() {
        districts.clear();
        corridors.clear();
        assets.clear();
    }
}
