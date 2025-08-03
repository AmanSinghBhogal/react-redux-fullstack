import React, { useState } from 'react';
import './NoteEditor.css';
import { useDispatch, useSelector } from 'react-redux';
import { PiTextbox } from 'react-icons/pi';
import noteService from '../../api/NotesService';
import { addNote } from '../../reducers/notes/NoteSlice';

const NoteEditor = () => {

  const uid = useSelector(state => state.users.user.id);
  const isAuthenticated = useSelector(state=> state.users.isAuthenticated);
  const userData = useSelector(state=> state.users.user);
  const dispatch = useDispatch();

  const [noteData, setnoteData] = useState({
    user_id:'',
    title: '',
    content: '',
    color: '',
    created_at: '',
    updated_at:''
  });

  function handleNote(e){
    e.preventDefault();
    console.log('uid:: '+uid);
    const newNoteData = {
      ...noteData,
      user_id: uid
    };
    setnoteData(newNoteData);
    console.log("noteData");
    console.log(newNoteData);

    noteService.postNote(newNoteData, userData)
      .then(
        (response)=> {
          console.log("httpResponse is::");
          console.log(response);
          console.log("data: ");
          console.log(response?.data?.response?.data);
        const noteData = response?.data?.response?.data;

        if(response.status === 200){

          dispatch(addNote(noteData));
        }
        else{
          alert(response.name+ ":: "+response.message);
        }
        }
      )
    }

  return (
    <div class='noteEditorContainer'>
      {
      isAuthenticated?
      <>
        <form onSubmit={handleNote}>
          <div>
            <span>Title:  </span>
            <input type='text' placeholder="Write Title..." 
                   onChange={(e) => {
                      setnoteData(
                        {
                          ...noteData,
                          title: e.target.value
                        }
                      )
                   }}
                required />
          </div>
          <div>
            <span>Content:  </span>
            <textarea placeholder='Enter your message here'
              onChange={(e)=> {
                setnoteData(
                  {
                    ...noteData,
                    content: e.target.value
                  }
                )
              }}
            required/>
          </div>
          <div>
            <span>Color:  </span>
            <input type='color' 
              onChange={(e)=> {
                setnoteData(
                  {
                    ...noteData,
                    color: e.target.value
                  }
                )
              }}
            />
          </div>
          <button type='submit' >Submit</button>
        </form>
      </>
      :
        <h1>Please Login to edit or create note.</h1>
      }
      
    </div>
  )
}

export default NoteEditor;