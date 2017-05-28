package rs.elfak.bobans.carsharing.utils;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;

/**
 * Picture utility class. Used for loading web images using Glide library.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class PictureUtils {

    public static void loadImage(final String photoUrl, final ImageView imageView) {
        Glide.with(CarSharingApplication.getInstance())
                .load(photoUrl)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImage(final String photoUrl, Transformation<Bitmap> transformation, final ImageView imageView) {
        Glide.with(CarSharingApplication.getInstance())
                .load(photoUrl)
                .bitmapTransform((Transformation) transformation)
                .into(imageView);
    }

    public static void loadImage(final String photoUrl, @DrawableRes int placeholder, final ImageView imageView) {
        Glide.with(CarSharingApplication.getInstance())
                .load(photoUrl)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImage(final String photoUrl, Transformation<Bitmap> transformation, @DrawableRes int placeholder, final ImageView imageView) {
        Glide.with(CarSharingApplication.getInstance())
                .load(photoUrl)
                .bitmapTransform((Transformation) transformation)
                .placeholder(placeholder)
                .into(imageView);
    }

}
