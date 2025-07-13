// In redux toolkit reducers are call Slice.
import { createSlice } from "@reduxjs/toolkit";


const initialState = {
    notes: [{}]
}

// state - this variable gives you access to the current state of that slice in our store.
// action - this variable provides you the input values needed to perform an action on data in our store.



// We create slice in the followin format it mainly needs 3 things:
// 1. Name - It is simply a logical name used to identify the Slice
// 2. InitialState - What should be the initial state for this slice in our store.
// 3. reducers - These are the functions that will allow you to manipulate the data in our store.
export const noteSlice = createSlice({
    name: 'noteSlice',
    initialState,
    reducers: {
        fetchNotes: (state, action) => {
                        state.notes = action.payload;
                    },
        addNote: PostNote,
        removeNote: () => {},
        updateNote: () => {}
    }
});

// Export the individual functionality.
export const {fetchNotes, addNote, removeNote, updateNote} = noteSlice.actions

// Export all reducers for the store:
export default noteSlice.reducer



function PostNote(state, action) {
    console.log("Post note invoked");
    const note = {
        user_id: action.payload.user_id,
        title: action.payload.title,
        content: action.payload.content,
        color: action.payload.color,
        created_at: null,
        updated_at: null
    };
    state.notes.push(note);
}