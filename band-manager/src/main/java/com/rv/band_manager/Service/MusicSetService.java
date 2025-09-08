package com.rv.band_manager.Service;

import com.rv.band_manager.Model.Band;
import com.rv.band_manager.Model.MusicPart;
import com.rv.band_manager.Model.MusicSet;

import java.util.List;
import java.util.Optional;

public interface MusicSetService {
    List<MusicSet> getAllMusicSets();
    Optional<MusicSet> getMusicSetById(Long id);
    MusicSet saveMusicSet(MusicSet musicSet);
    MusicSet updateMusicSet(Long id, MusicSet updatedMusicSet);
    List<MusicSet> getMusicSetsByBand(String bandName);
    MusicSet addBandToMusicSet(Long MusicSetId, Long BandId);
    void deletePractice(Long musicSetId);
}
