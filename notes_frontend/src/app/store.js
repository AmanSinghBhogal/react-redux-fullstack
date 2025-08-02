// Step 1: bring configureStore it will help you to create the data store.
import { configureStore } from "@reduxjs/toolkit";
import noteReducer from "../reducers/notes/NoteSlice";
import userReducer from "../reducers/users/UserSlice";

export const store = configureStore({
    reducer: {
        notes: noteReducer,
        users: userReducer
    }
});
