// import { useState } from 'react'
import { useDispatch } from 'react-redux';
import './App.css'
import Notes from './components/notes/Notes'
import { useEffect, useState } from 'react';
import noteService from './api/NotesService';
import { fetchNotes } from './reducers/notes/NoteSlice';

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

      
    }, []);

  return (
    <>
      <Notes loading={loading} />
    </>
  )
}

export default App
