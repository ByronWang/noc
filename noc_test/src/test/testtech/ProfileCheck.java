package testtech;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class ProfileCheck implements Command {

	public Profile newProfile(Context context) {
		return new Profile();
	}

	public boolean execute(Context context) throws Exception {
		Object profile = context.get(Profile.PROFILE_KEY);
		if (null == profile) {
			profile = newProfile(context);
			context.put(Profile.PROFILE_KEY, profile);
		}
		return false;
	}
}
