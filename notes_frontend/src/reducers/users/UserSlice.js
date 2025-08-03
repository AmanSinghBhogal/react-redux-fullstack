// In redux toolkit reducers are call Slice.
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    user: {},
    isAuthenticated: false
}


export const userSlice = createSlice({
    name: 'userSlice',
    initialState,
    reducers: {
        login: (state, action) => {
            console.log("Redux login Payload is: ");
            console.log(action.payload);
            state.isAuthenticated = true;
            state.user = action.payload;
            localStorage.setItem('userData', JSON.stringify({...action.payload}))
        },
        updateUser: () => {
            console.log("Updating user in store...")
        },
        logout: (state) => {
            state.user = {};
            state.isAuthenticated = false;
            localStorage.clear();
        }
    }
})

export const {login, updateUser, logout} = userSlice.actions;

export default userSlice.reducer;

