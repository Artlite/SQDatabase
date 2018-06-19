package com.artlite.sqltest.providers;

import android.content.Context;

import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqltest.models.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider which allow to get of the {@link List} of the {@link User}
 */

public final class UserListProvider implements BaseProvider<List<User>, Context> {
    /**
     * Method which provide the getting of the required objects
     *
     * @param parameters list of parameters
     * @return instance of required {@link Object}
     */
    @Override
    public List<User> get(Context... parameters) {
        List<User> users = new ArrayList<>();
        if ((parameters != null) && (parameters.length >= 1)) {
            final Context context = parameters[0];
            if (context != null) {
                for (int i = 0; i < 50; i++) {
                    final String name = SQRandomHelper.generateSentence(context, 1);
                    final String surname = SQRandomHelper.generateSentence(context, 1);
                    final String about = SQRandomHelper.generateSentence(context, 10);
                    final User user = new User(name, surname, about, null);
                    user.setSursurname(SQRandomHelper.generateSentence(context, 10));
                    users.add(user);
                }
            }
        }
        return users;
    }
}
