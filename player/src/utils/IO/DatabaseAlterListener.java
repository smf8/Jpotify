package utils.IO;

import Model.Song;

public interface DatabaseAlterListener {
    void removeSong(Song song);
    void saveSong(Song song);
}
