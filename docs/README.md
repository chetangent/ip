# Rudra User Guide

Rudra is a desktop task chatbot. Use the chat field to enter commands and press Enter or select Send. Your task list is saved automatically in `data/rudra.txt`.

## Starting Rudra

Run `./gradlew run` from the project root. The JavaFX window opens with a command guide in the left panel and your task conversation on the right.

## Managing tasks

Add a todo:

```text
todo borrow book
```

Add a deadline or event:

```text
deadline submit report /by 2026-09-10
event team sync /from 2026-09-08 1400 /to 2026-09-08 1500
```

View, search, and update tasks:

```text
list
find book
mark 1
unmark 1
delete 1
```

Enter `bye` to end the current GUI conversation.
