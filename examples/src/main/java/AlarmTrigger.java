import com.telekom.m2m.cot.restsdk.CloudOfThingsPlatform;
import com.telekom.m2m.cot.restsdk.alarm.Alarm;
import com.telekom.m2m.cot.restsdk.alarm.AlarmApi;
import com.telekom.m2m.cot.restsdk.devicecontrol.CotCredentials;
import com.telekom.m2m.cot.restsdk.inventory.ManagedObject;

import java.util.Arrays;
import java.util.Date;

/**
 * The AlarmTrigger sends an Alarm to the Cloud of Things.
 */
public class AlarmTrigger {

    private static void printHelpAndExit() {
        System.out.println("Arguments need to be: <deviceId> <alarmType> <alarmSeverity> <alarmText>");
        System.out.println("The deviceId must be valid. Otherwise you get status 422, 'alarm/Unprocessable Entity'");
        System.out.println("The alarmType is something like com_mycompany_something_specialalarm.");
        System.out.println("The alarmSeverity is WARNING|MINOR|MAJOR|CRITICAL.");
        System.out.println("The alarmText is any user (or device) supplied description. Spaces are allowed.");
        System.out.println("Note that if you create multiple alarms with the same type and severity they will " +
                           "not be seen as individual alarms, but as one, with an increasing count.");
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            printHelpAndExit();
        }
        CloudOfThingsPlatform platform = createPlatform();
        AlarmApi alarmApi = platform.getAlarmApi();

        Alarm alarm = new Alarm();
        ManagedObject source = new ManagedObject(); // This would be the device, that sends the alarm.
        source.setId(args[0]);
        alarm.setSource(source);
        alarm.setType(args[1]);
        alarm.setSeverity(args[2]);
        alarm.setText(String.join(" ", Arrays.copyOfRange(args, 3, args.length)));
        alarm.setTime(new Date());
        alarm.setStatus(Alarm.STATE_ACTIVE);

        alarmApi.create(alarm);

        System.out.println("The alarm has been created with id " + alarm.getId());
    }

    private static CloudOfThingsPlatform createPlatform() {
        // Ensure that you populate the required environment variables with your credentials.
        String host = System.getenv("COT_REST_CLIENT_HOST");
        if (host == null) {
            throw new RuntimeException("Cloud of Things host missing. Provide it via environment variable 'COT_REST_CLIENT_HOST'.");
        }
        String tenant = System.getenv("COT_REST_CLIENT_TENANT");
        if (tenant == null) {
            throw new RuntimeException("Cloud of Things tenant missing. Provide it via environment variable 'COT_REST_CLIENT_TENANT'.");
        }
        String user = System.getenv("COT_REST_CLIENT_USER");
        if (user == null) {
            throw new RuntimeException("Cloud of Things user missing. Provide it via environment variable 'COT_REST_CLIENT_USER'.");
        }
        String password = System.getenv("COT_REST_CLIENT_PASSWORD");
        if (password == null) {
            throw new RuntimeException("Cloud of Things password missing. Provide it via environment variable 'COT_REST_CLIENT_PASSWORD'.");
        }
        // From the platform we can get the numerous APIs, for example the AlarmApi:
        return new CloudOfThingsPlatform(host, new CotCredentials(tenant, user, password));
    }

}
