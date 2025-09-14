import React, { useEffect, useState } from 'react';
import './NoteEditor.css';
import { useDispatch, useSelector } from 'react-redux';
import { PiTextbox } from 'react-icons/pi';
import noteService from '../../api/NotesService';
import { addNote, updateNote} from '../../reducers/notes/NoteSlice';
import { Link, useNavigate } from 'react-router';

const NoteEditor = () => {

  const uid = useSelector(state => state.users.user.id);
  const isAuthenticated = useSelector(state=> state.users.isAuthenticated);
  const userData = useSelector(state=> state.users.user);
  const editNoteData = useSelector(state=> state.notes.editNote);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [noteData, setnoteData] = useState({
    user_id:'',
    title: '',
    content: '',
    color: '',
    created_at: '',
    updated_at:''
  });


  useEffect(() => {
  console.log("useEffect Called", editNoteData);
  if (editNoteData) {
    setnoteData({
      user_id: editNoteData.user_id || '',
      title: editNoteData.title || '',
      content: editNoteData.content || '',   
      color: editNoteData.color || '',
      created_at: editNoteData.created_at || '',
      updated_at: editNoteData.updated_at || ''
    });
  }
}, [editNoteData]);
  

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
    
    if(editNoteData){
      let patchData = {
        id: editNoteData.id,
        updated_at: ''
      }

      for(let key in noteData){
        if(noteData[key] !== editNoteData[key]){
          patchData = {
            ...patchData,
            [key]: noteData[key]
          }
        }
      }

      console.log("Final Data going in Patch Request is::");
      console.log(patchData);
      
      noteService.patchNote(patchData, userData)
        .then((response) => {
            console.log("httpResponse is::");
            console.log(response);
            console.log("data");
            console.log(response?.data?.response?.data);
            const editedNoteReponse = response?.data?.response?.data;

            if(response.status === 200){
              dispatch(updateNote(editedNoteReponse));
              navigate("/");
            }
            else{
            alert(response.name+ ":: "+response.message);
          }
        })
    }
    else{
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
    }

  return (
    <div class='noteEditorContainer'>
      {
      isAuthenticated?
      <>
        <form onSubmit={handleNote}>
          <div>
            {
            editNoteData?.title? 

            <h1>Edit Your Note Here</h1>
            : 
            <h1>Create New Note</h1>
            }
          </div>
          <div>
            <span>Title:  </span>
            <input type='text' placeholder="Write Title..."  value={noteData.title} 
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
            <textarea placeholder='Enter your message here' value={noteData.content}
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
            <input type='color' value={noteData.color}
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
          <button className='text-gray-800 bg-orange-500 focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 focus:outline-none' type='submit' >
            {
              editNoteData?.title? 
                "Update"
              : "Submit"
            }
          </button>
        </form>
      </>
      :
        <h1>Please Login to edit or create note.</h1>
      }
      
    </div>
  )
}

export default NoteEditor;