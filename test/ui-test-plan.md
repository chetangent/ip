# UI Test Plan

Record UI regression checks for the chatbot here. Each test case describes one fresh program run with the exact console input and expected console output.

## Farewell smoke test
Aim: Verify that the chatbot starts correctly and exits cleanly when the user enters bye.

### Inputs
```text
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Add and list a todo
Aim: Verify that a valid todo is added and later shown in the task list.

### Inputs
```text
todo borrow book
list
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Reject an empty todo
Aim: Verify that a todo without a description is rejected with a clear error message.

### Inputs
```text
todo
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
The description of a todo cannot be empty.
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Reject an unknown command
Aim: Verify that an unrecognized command is rejected instead of being treated as a task.

### Inputs
```text
blah
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
I don't recognize that command yet. Try todo, deadline, event, list, mark, unmark, or delete.
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Preserve task state after a mark error
Aim: Verify that an invalid mark command reports the error and does not accidentally modify the existing task list.

### Inputs
```text
todo borrow book
mark two
list
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
Task numbers should be whole numbers.
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Reject malformed deadline input
Aim: Verify that a deadline missing the /by section is rejected with format guidance.

### Inputs
```text
deadline return book
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
Please use: deadline DESCRIPTION /by WHEN
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Delete a task from the middle of the list
Aim: Verify that deleting a task removes the correct item and updates the task count.

### Inputs
```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
delete 2
list
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
Got it. I've added this task:
[T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
Got it. I've added this task:
[D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
Got it. I've added this task:
[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
Noted. I've removed this task:
[D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Preserve task state after an invalid delete
Aim: Verify that a malformed delete command reports the error and does not remove any tasks.

### Inputs
```text
todo borrow book
delete two
list
bye
```

### Expected Output
```text
____________________________________________________________
 ____            _            
|  _ \ _   _  __| |_ __ __ _ 
| |_) | | | |/ _` | '__/ _` |
|  _ <| |_| | (_| | | | (_| |
|_| \_\\__,_|\__,_|_|  \__,_|

Hello! I'm Rudra.
What can I do for you?
____________________________________________________________
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
Task numbers should be whole numbers.
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
