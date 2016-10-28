package rs.elfak.bobans.carsharing.utils;

import java.util.regex.Pattern;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com
 */

public class Constants {

    public static final long SPLASH_SCREEN_MINIMAL_DURATION = 1500;

    public static final int USERNAME_MINIMUM_LENGTH = 6;
    public static final int PASSWORD_MINIMUM_LENGTH = 8;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern LICENSE_PLATES_REGEX = Pattern.compile("^[A-Z]{2}\\s{1}[0-9]{3,}\\-[A-Z]{2}");

    public static final int MIN_CAR_YEAR = 1950;
    public static final int MAX_CAR_YEAR = 2199;
}
