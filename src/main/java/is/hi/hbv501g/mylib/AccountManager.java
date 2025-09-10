package is.hi.hbv501g.mylib;
import java.sql.*;
import java.util.Objects;
/*
The Account Controller class handles all modification of an account and authentication with the server. Database is
still missing at this time, assumed to be named database, and a table of users exists
 */
public class AccountManager {

    /*
    login takes a username and password and checks if a corresponding entry exist in database. If the account details
    exist, it validates the session by creating an Account instance for local credentials.
     */

    public Account login(String username, String password) {
        String query = "SELECT username, created_at FROM users WHERE username = ? AND password = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); //  Replace with hashed password check

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getString("username"), rs.getString("bio"));
            } else {
                System.out.println("Login failed: username or password incorrect.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    create account creates a new account entry in the users database, using a username and password from the new user.
    if the username is taken, the method returns false and the account creation fails. if it is successful, the account
    is created and can be logged into
     */
    public boolean createAccount(String username, String password, String confirmPassword) {
        if(Objects.equals(password, confirmPassword) && password != null){
            if (password != null){
                String query = "INSERT INTO users (username = ?, password = ?)";
                try (Connection conn = Database.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, username);
                    stmt.setString(2, password); //  Replace with hashed password check

                    stmt.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    if (e.getMessage().contains("Duplicate entry")) {
                        System.out.println("Username already exists.");
                    } else {
                        e.printStackTrace();
                    }
                    return false;
                }
            } else {System.out.println("password cannot be null"); return false;}
        } else { System.out.println("passwords dont match"); return false;}
    }
    /*
        updateUsername uses the stored credentials and changes the bio in the database. if that is successful, the
        current session bio is also updated.
    */
    public boolean updateBio(Account account, String newBio) {
        String query = "UPDATE users SET bio = ? WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newBio);
            stmt.setString(2, account.getUsername());

            int rows = stmt.executeUpdate(); // commit to db
            if (rows > 0) {
                // update cached object
                account.setBio(newBio);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    updateUsername uses the stored credentials and changes the username in the database. if that is successful, the
    current session credentials are also updated.
     */
    public boolean updateUsername(Account account, String newUsername) {
        String query = "UPDATE users SET bio = ? WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newUsername);
            stmt.setString(2, account.getUsername());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                // update cached object
                account.setUsername(newUsername);
                return true;
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Username is taken.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    /*
    update password takes the current session credentials account, the password to the current session and the new
    password. It compares the stored username and the given password with the database, and if the password is correct,
    replaces the old password in that database entry with the new one. if this action completes, the method returns true.
    */
    public boolean updatePassword(Account account, String oldPassword, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ? AND password = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, account.getUsername());
            stmt.setString(3, oldPassword);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Password updated");
                return true;
            } else {
                System.out.println("incorrect password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    logout nulls all stored account details for the session.
     */
    public void logout(Account account){
        account = null;
        System.out.println("user has logged out");
    }
}
