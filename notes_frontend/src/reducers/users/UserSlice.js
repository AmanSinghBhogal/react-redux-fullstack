// In redux toolkit reducers are call Slice.
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    user: {}
}


export const userSlice = createSlice({
    name: 'userSlice',
    initialState,
    reducers: {
        login: (state, action) => {
            console.log("Redux login Payload is: "+action.payload);
            console.log(action.payload);
            state.user = action.payload;
        },
        updateUser: () => {
            console.log("Updating user in store...")
        },
        logout: (state) => {
            state.user = {}
            localStorage.clear();
        }
    }
})

export const {login, updateUser, logout} = userSlice.actions;

export default userSlice.reducer;

