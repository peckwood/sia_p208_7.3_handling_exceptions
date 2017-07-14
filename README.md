spec: Servlet 3.1 tomcat: 8.5

### Mapping exceptions to HTTP status codes 

#### Notable files:

- `spittr.exception.SpittleNotFoundException` (the exception)
- `spittr.web.SpittleController`'s method `spittle()` (throws exception)

---

### Writeing exception-handling method (catch exceptions thrown in the same controller)

Notable files:

- `spittr.exception.DuplicateSpittleException` exception
- `spittr.web.SpittleController`'s method `saveSpittle` throws exception
- `spittr.web.SpittleController`'s method `handleDuplicateSpittle` handles the exception
- `/main/webapp/WEB-INF/views/error/duplicate.jsp` shows the message to user

---

### Writeing controller advice (catch exceptions thrown app-wide)

Notable files:

- `spittr.exception.DuplicateSpittleExceptionGlobal` exception
- `spittr.web.SpittleController`'s method `saveSpittle` throws exception
- `spittr.web.AppWideExceptionHandler`' handles the exception
- `/main/webapp/WEB-INF/views/error/duplicate-global.jsp` shows the message to user



