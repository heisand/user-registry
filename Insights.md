
## Technical decisions

### Backend

The backend is written as a Spring Boot application, with Gradle as build tool. 

It uses an in-memory database, leveraging H2. H2 is for this assignment chosen due to its lightweight and simplicity.

It uses Hibernate as ORM because it allows for a fast development cycle, with features such as automatic schema generation and mapping.

In real use, there are of course other considerations to make.


### Frontend

The frontend is written as an React TypeScript application, using Vite as build tool and development server.

It leverages [Chakra UI](https://v2.chakra-ui.com/) as component library, due to its modular and accessible components.

## Future improvements

### Frontend

Due to time limitations, the frontend does not yet support much feedback on actions performed, which makes it hard for a user to follow along. 

A better solution will be to not only swallow any errors, but also act upon them and show feecback to users in the form of error messages and 
indications of successful actions.

Some components would also benefit from being made more general so that they can be reused.

The frontend should also be made responsive, so that it adjusts to different screen sizes.

### Backend

CORS can be handled globally instead of per controller.

For better security, the endpoints can be secured.

And finally, a working Dockerization can be set up.
