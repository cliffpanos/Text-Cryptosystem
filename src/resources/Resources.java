package resources;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.image.Image;


public class Resources {

    private static Resources instance = new Resources();
    private static MediaPlayer nowPlaying = null;

    private Resources() {

    }

    public static Image getImage(String fileName) {

        //fill filePath
        String filePath = "images/" + fileName;

        return new Image(instance.getClass().getResource(
            filePath).toExternalForm());
    }

    public static void playSound(String soundName) {

        if (nowPlaying != null) {
            nowPlaying.stop(); //Stop last sound from continuing to play
        }

        //full filePath
        String filePath = "sounds/" + soundName;

        nowPlaying = new MediaPlayer(new Media(instance.getClass()
            .getResource(filePath).toExternalForm()));

        nowPlaying.play();
    }

}
