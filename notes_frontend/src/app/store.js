// Step 1: bring configureStore it will help you to create the data store.
import { configureStore } from "@reduxjs/toolkit";
import noteReducer from "../reducers/notes/NoteSlice";

export const store = configureStore({
    reducer: noteReducer
});