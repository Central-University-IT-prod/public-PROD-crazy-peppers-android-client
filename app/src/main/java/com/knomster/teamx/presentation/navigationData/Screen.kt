package com.knomster.teamx.presentation.navigationData

sealed class Screen(
    val route: String
) {

    data object LoginScreen : Screen(ROUTE_LOGIN_SCREEN)
    data object AdminScreen : Screen(ROUTE_ADMIN_SCREEN)
    data object AdminViewScreen : Screen(ROUTE_ADMIN_VIEW_SCREEN)
    data object CreateTeamScreen : Screen(ROUTE_CREATE_TEAM_SCREEN)
    data object ParticipantScreen : Screen(ROUTE_PARTICIPANT_SCREEN)
    data object CreateOlympiadScreen : Screen(ROUTE_CREATE_OLYMPIAD_SCREEN)
    data object AdminParticipantsScreen : Screen(ROUTE_ADMIN_PARTICIPANTS_SCREEN)
    data object AdminTeamsScreen : Screen(ROUTE_ADMIN_TEAMS_SCREEN)
    data object UserNotificationsScreen : Screen(ROUTE_USER_NOTIFICATIONS_SCREEN)
    data object CaptainNotificationsScreen : Screen(ROUTE_CAPTAIN_NOTIFICATIONS_SCREEN)
    data object UserTeamsScreen : Screen(ROUTE_USER_TEAMS_SCREEN)
    data object CaptainUsersScreen : Screen(ROUTE_CAPTAIN_USERS_SCREEN)
    data object CaptainTeamScreen : Screen(ROUTE_CAPTAIN_TEAM_SCREEN)
    data object UserTeamScreen : Screen(ROUTE_USER_TEAM_SCREEN)
    data object UserTeamsAndTeamScreen : Screen(ROUTE_USER_TEAMS_AND_TEAM_SCREEN)
    data object CaptainUsersAndUserScreen : Screen(ROUTE_CAPTAIN_USERS_AND_USER_SCREEN)

    data object UserDetailTeamScreen : Screen(ROUTE_USER_DETAIL_TEAM_SCREEN) {
        private const val ROUTE_FOR_ARGS = "user_detail_team_screen"

        fun getRouteWithArgs(id: String) = "$ROUTE_FOR_ARGS/$id"
    }

    data object CaptainDetailUserScreen : Screen(ROUTE_CAPTAIN_DETAIL_USER_SCREEN) {
        private const val ROUTE_FOR_ARGS = "captain_detail_user_screen"

        fun getRouteWithArgs(id: String) = "$ROUTE_FOR_ARGS/$id"
    }

    data object UserDetailTeamScreenNotification :
        Screen(ROUTE_USER_DETAIL_TEAM_SCREEN_NOTIFICATION) {
        private const val ROUTE_FOR_ARGS = "user_detail_team_screen_notification"

        fun getRouteWithArgs(id: String) = "$ROUTE_FOR_ARGS/$id"
    }

    data object CaptainDetailUserScreenNotification :
        Screen(ROUTE_CAPTAIN_DETAIL_USER_SCREEN_NOTIFICATION) {
        private const val ROUTE_FOR_ARGS = "captain_detail_user_screen_notification"

        fun getRouteWithArgs(id: String) = "$ROUTE_FOR_ARGS/$id"
    }


    data object AdminDetailTeamScreen : Screen(ROUTE_ADMIN_DETAIL_TEAM_SCREEN) {
        private const val ROUTE_FOR_ARGS = "admin_detail_team_screen"

        fun getRouteWithArgs(id: String) = "$ROUTE_FOR_ARGS/$id"
    }

    data object AdminDetailUserScreen : Screen(ROUTE_ADMIN_DETAIL_USER_SCREEN) {
        private const val ROUTE_FOR_ARGS = "admin_detail_user_screen"

        fun getRouteWithArgs(id: String) = "$ROUTE_FOR_ARGS/$id"
    }

    private companion object {
        const val ROUTE_ADMIN_SCREEN = "admin_screen"
        const val ROUTE_ADMIN_VIEW_SCREEN = "admin_view_screen"
        const val ROUTE_PARTICIPANT_SCREEN = "participant_screen"
        const val ROUTE_LOGIN_SCREEN = "login_screen"

        const val ROUTE_CREATE_TEAM_SCREEN = "create_team_screen"

        const val ROUTE_CREATE_OLYMPIAD_SCREEN = "create_olympiad_screen"
        const val ROUTE_ADMIN_PARTICIPANTS_SCREEN = "admin_participants_screen"
        const val ROUTE_ADMIN_TEAMS_SCREEN = "admin_teams_screen"

        const val ROUTE_USER_NOTIFICATIONS_SCREEN = "user_notifications_screen"
        const val ROUTE_CAPTAIN_NOTIFICATIONS_SCREEN = "captain_notifications_screen"

        const val ROUTE_USER_TEAMS_SCREEN = "user_teams_screen"
        const val ROUTE_CAPTAIN_USERS_SCREEN = "captain_users_screen"

        const val ROUTE_CAPTAIN_TEAM_SCREEN = "captain_team_screen"
        const val ROUTE_USER_TEAM_SCREEN = "user_team_screen"

        const val ROUTE_USER_TEAMS_AND_TEAM_SCREEN = "user_teams_and_team_screen"
        const val ROUTE_CAPTAIN_USERS_AND_USER_SCREEN = "captain_users_and_user_screen"

        const val ROUTE_USER_DETAIL_TEAM_SCREEN = "user_detail_team_screen/{id}"
        const val ROUTE_CAPTAIN_DETAIL_USER_SCREEN = "captain_detail_user_screen/{id}"

        const val ROUTE_USER_DETAIL_TEAM_SCREEN_NOTIFICATION = "user_detail_team_screen_notification/{id}"
        const val ROUTE_CAPTAIN_DETAIL_USER_SCREEN_NOTIFICATION = "captain_detail_user_screen_notification/{id}"

        const val ROUTE_ADMIN_DETAIL_TEAM_SCREEN = "admin_detail_team_screen/{id}"
        const val ROUTE_ADMIN_DETAIL_USER_SCREEN = "admin_detail_user_screen/{id}"
    }
}
