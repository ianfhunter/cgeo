package cgeo.geocaching.ui.notifications;

import cgeo.geocaching.CgeoApplication;
import cgeo.geocaching.R;
import cgeo.geocaching.settings.Settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.function.Function;

public class Notifications {
    /**
     * A fresh not yet used notification id can be gathered via {@link cgeo.geocaching.settings.Settings#getUniqueNotificationId}
     */
    public static final int UNIQUE_ID_RANGE_START = 1000;

    public static final int ID_PROXIMITY_NOTIFICATION = 101;

    public static final int ID_FOREGROUND_NOTIFICATION_MAP_IMPORT = 111;
    public static final int ID_FOREGROUND_NOTIFICATION_CACHES_DOWNLOADER = 112;
    public static final int ID_FOREGROUND_NOTIFICATION_SPEECH_SERVICE = 113;

    public static final int ID_WHERIGO_SERVICE_NOTIFICATION_ID = 114;
    public static final int ID_WHERIGO_NEW_DIALOG_ID = 115;

    private Notifications() {
        // no instances
    }

    public static NotificationManagerCompat getNotificationManager(@Nullable final Context context) {
        return NotificationManagerCompat.from(context == null ? CgeoApplication.getInstance() : context);
    }

    public static void send(@Nullable final Context context, @Nullable final Integer id, @NonNull final NotificationChannels channel, @NonNull final Function<NotificationCompat.Builder, NotificationCompat.Builder> builderFunction) {
        final NotificationCompat.Builder builder = newBuilder(context, channel);
        send(context, id, builderFunction.apply(builder));
    }

    public static void send(@Nullable final Context context, @Nullable final Integer id, @NonNull final NotificationCompat.Builder notificationBuilder) {
        getNotificationManager(context).notify(id == null ? Settings.getUniqueNotificationId() : id, notificationBuilder.build());
    }

    public static NotificationCompat.Builder newBuilder(@Nullable final Context context, final NotificationChannels channel) {
        return new NotificationCompat.Builder(context == null ? CgeoApplication.getInstance() : context, channel.name())
                .setSmallIcon(R.drawable.cgeo_notification);
    }

    public static NotificationCompat.Builder createNotification(final Context context, final NotificationChannels channel, final String title) {
        return newBuilder(context, channel)
                .setContentTitle(title);
    }

    public static NotificationCompat.Builder createNotification(final Context context, final NotificationChannels channel, final int title) {
        return createNotification(context, channel, context.getString(title));
    }

    public static NotificationCompat.Builder createTextContentNotification(final Context context, final NotificationChannels channel, final String title, final String text) {
        return createNotification(context, channel, title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));
    }

    public static NotificationCompat.Builder createTextContentNotification(final Context context, final NotificationChannels channel, final int title, final String text) {
        return createTextContentNotification(context, channel, context.getString(title), text);
    }

    public static void cancel(@Nullable final Context context, final int id) {
        getNotificationManager(context).cancel(id);
    }
}
