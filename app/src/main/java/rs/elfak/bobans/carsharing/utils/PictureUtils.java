package rs.elfak.bobans.carsharing.utils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Picture utility class. Used for loading web images using Glide library.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class PictureUtils {

    public static void loadImage(final String photoUrl, final ImageView imageView) {
        Glide.with(CarSharingApplication.getInstance())
                .load(photoUrl)
                .into(imageView);
    }

    public static void loadImage(final String photoUrl, @DrawableRes int placeholder, final ImageView imageView) {
        Glide.with(CarSharingApplication.getInstance())
                .load(photoUrl)
                .placeholder(placeholder)
                .into(imageView);
    }

}
