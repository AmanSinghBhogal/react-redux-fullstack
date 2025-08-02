import React from 'react';
import './NavBar.css';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../../reducers/users/UserSlice';


const NavBar = () => {

  const userdetail = useSelector(state => state.users.user);
  const dispatch= useDispatch();

  function logout1(){
    dispatch(logout());
  }
  
  return (
    <div class='navBarContainer'>
        <span class='appName'>Practice Notes</span>
        {
          userdetail.username ? 
          <>
            <span>
              {userdetail.username}
            </span>
            <button onClick={logout1} >LogOut</button>
          </>
          :
          <a class='loginBtn' href='#login'>Login/Register</a>
        }
    </div>
  )
}

export default NavBar;