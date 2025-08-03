import React from 'react';
import './NavBar.css';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../../reducers/users/UserSlice';


const NavBar = () => {

  const userdetail = useSelector(state => state.users.user);
  const dispatch= useDispatch();

  const logout1 = (e) => {
    console.log(e);
    dispatch(logout());
  }
  
  return (
    <div class='navBarContainer'>
        <span class='appName'>Practice Notes</span>
        {
          userdetail.username ? 
            <form onSubmit={logout1}>
              <span>
                {userdetail.username}
              </span>
              <button type='submit'>LogOut</button>
            </form>
          :
          <a class='loginBtn' href='#login'>Login/Register</a>
        }
    </div>
  )
}

export default NavBar;