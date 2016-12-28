package resources;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Resources {

    private static Resources instance = new Resources();
    private static MediaPlayer nowPlaying = null;

    private Resources() {
        //private constructor so that only one Resources instance can be made
    }

    public static Image getImage(String fileName) {

        //full filePath
        String filePath = "images/" + fileName;

        return new Image(instance.getClass().getResource(
            filePath).toExternalForm());
    }


    public static ImageView getImageView(String fileName, double dimension) {

        Image image = getImage(fileName);
        ImageView imageView = new ImageView(image);

        dimension = (fileName.equals("decrypt.png") ? (1.17 * dimension)
            : dimension); //System-wide, the decrypt icon needs to be larger

        //Resize and resample every ImageView's icon image
        imageView.setFitWidth(dimension);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        return imageView;
    }


    public static void playSound(String soundName) {

        if (nowPlaying != null) {
            nowPlaying.stop();
            //Stop last sound from continuing to play
        }

        //full filePath
        String filePath = "sounds/" + soundName;

        nowPlaying = new MediaPlayer(new Media(instance.getClass()
            .getResource(filePath).toExternalForm()));

        nowPlaying.play();
    }

}
