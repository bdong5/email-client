# Email Client

**🚧 Work in Progress 🚧**

## Overview

This Email Client is a cross-platform desktop application designed to manage and organize your email communications efficiently. Built using Java Swing and FlatLaf, it offers a user-friendly interface with essential features like email composition, sending, receiving, and integration with SMTP and IMAP protocols. Please note that this project is still a work in progress, with particular focus needed on UI design.

## Features

- **Compose Emails**: Write and send emails with an intuitive and straightforward interface.
- **Receive Emails**: Check and manage incoming emails with integration to IMAP servers.
- **SMTP Integration**: Seamlessly send emails using your preferred SMTP server.
- **Cross-Platform Compatibility**: Runs smoothly on Windows, macOS, and Linux.
- **Modern UI**: Designed with FlatLaf for a sleek and modern user experience, though further enhancements are planned.

## Technologies Used

- **Java**: The primary programming language used for application development.
- **Java Swing**: Utilized for building the graphical user interface.
- **FlatLaf**: A modern Look and Feel for Swing, providing a polished UI experience.
- **SMTP/IMAP**: Protocols used for sending and receiving emails.

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/bdong5/email-client.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd email-client
   ```
3. **Build the Project**:
   - If you're using an IDE like IntelliJ IDEA or Eclipse, import the project and build it using the provided configurations.
   - If you're building from the command line, ensure that you have Gradle or Maven set up, then run:
     ```bash
     ./gradlew build
     ```
     or
     ```bash
     mvn clean install
     ```

4. **Run the Application**:
   - Execute the built `.jar` file:
     ```bash
     java -jar build/libs/email-client.jar
     ```

## Usage

- **Composing an Email**: Use the "Compose" button to open a new window for writing your email. Fill in the recipient, subject, and body fields before sending.
- **Checking Emails**: The application connects to your configured IMAP server to retrieve emails, displaying them in the inbox.
- **Settings**: Configure your SMTP and IMAP settings under the "Settings" menu to connect the application to your email account.

## Development Status

This project is still under development, with significant focus needed on improving the UI design. Upcoming work includes:

- **UI Enhancements**: Further refining the user interface to improve usability and visual appeal.
- **Improved Error Handling**: Ensuring robust handling of network issues, authentication failures, etc.
- **Additional Features**: Implementing more advanced email management features like folders, search, and filters.

## Contributing

If you would like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

None.

## Contact

For any questions, suggestions, or contributions, feel free to reach out to me!


