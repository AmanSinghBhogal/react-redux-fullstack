// import { useState } from 'react'
import { useDispatch } from 'react-redux';
import './App.css'
import Notes from './components/notes/Notes'
import { useEffect, useState } from 'react';
import noteService from './api/NotesService';
import { fetchNotes } from './reducers/notes/NoteSlice';
import NoteEditor from './components/noteEditor/NoteEditor';
import NavBar from './components/NavBar/NavBar';
import Auth from './components/Auth/Auth';
import {login} from './reducers/users/UserSlice';

function App() {
  const dispatch = useDispatch();

    const [loading, setLoading] = useState(true);

    useEffect(() => {
        noteService.getNotes()
                    .then((response) => {
                        console.log('data is:');
                        console.log(response?.data?.response?.data);
                        const data = response?.data?.response?.data
                        if(data != null)
                            dispatch(fetchNotes(data));
                    })
                    .finally(() => {
                        setLoading(false);
                    })

        if(localStorage.getItem("userData") !== null){
          console.log("User already present...");
          dispatch(login(JSON.parse(localStorage.getItem("userData"))));
        }
      
    }, []);

  return (
    <>
      <NavBar />
      <Notes loading={loading} />
      <NoteEditor />
      <Auth />
    </>
  )
}

export default App
