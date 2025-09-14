import { useDispatch } from 'react-redux';
import './App.css'
import { useEffect} from 'react';
import noteService from './api/NotesService';
import { fetchNotes, updateLoading } from './reducers/notes/NoteSlice';
// import {login} from './reducers/users/UserSlice';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import { Outlet } from 'react-router';
import { login } from './reducers/users/UserSlice';

function App() {
  
    const dispatch = useDispatch();

    useEffect(() => {
        console.log("Inside UseEffect");
        noteService.getNotes()
                    .then((response) => {
                        console.log('data is:');
                        console.log(response?.data?.response?.data);
                        const data = response?.data?.response?.data
                        if(data != null)
                            dispatch(fetchNotes(data));
                    })
                    .finally(() => {
                        dispatch(updateLoading(false));
                    })

        if(localStorage.getItem("userData") !== null){
          console.log("User already present...");
          dispatch(login(JSON.parse(localStorage.getItem("userData"))));
        }
      
    }, []);

  return (
    <>
      <Header />
      <Outlet />
      <Footer/>
    </>
  )
}

export default App
