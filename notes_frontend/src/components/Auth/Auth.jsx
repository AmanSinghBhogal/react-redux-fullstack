import React, { useState } from 'react';
import './Auth.css';
import { VscEye } from "react-icons/vsc";
import { VscEyeClosed } from "react-icons/vsc";
import userService from '../../api/UserService';
import { useDispatch } from 'react-redux';
import { login, logout } from '../../reducers/users/UserSlice';

const Auth = () => {

    const dispatch = useDispatch();

    const [SignUp, setSignUp] = useState(false);
    const [showPassword, setShowPassword] = useState(false)
    const [PersonalDetails, setPersonalDetails] = useState({
        username: '',
        password: '',
        email: ''
    });
    const [isAuthenticated, setIsAuthenticated] = useState(localStorage.getItem("userData")? true: false);

    function func(){
        dispatch(logout());
        setIsAuthenticated(false);
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            if(SignUp){
                console.log("Register user called.");
                console.log(PersonalDetails);
                userService.registerUser(PersonalDetails)
                    .then((httpReponse) => {
                        console.log("httpReponse is: ");
                        console.log(httpReponse);
                        const userData = httpReponse?.data?.response?.data;
                        console.log(userData);
                        console.log(PersonalDetails.password);
                        userData.password = PersonalDetails.password;
                        dispatch(login(PersonalDetails));
                        setIsAuthenticated(true);
                    })
                    .finally(() => {
                        console.log("User Registered Successfully.");
                    })
            } 
            else{
                console.log("Log In Called");
                userService.loginUser(PersonalDetails)
                    .then((httpResponse) => {
                        console.log("httpResponse is: ");
                        console.log(httpResponse);
                        const userData = httpResponse?.data?.response?.data;
                        dispatch(login(userData));
                        setIsAuthenticated(true);
                    })
                    .finally(() => {
                        console.log("User Logged In");
                    })
            }
        }catch(err){
            console.log(err);
        }
    }


  return (
    <div id='login' class='authContainer'>
        {
            isAuthenticated? 
            <div>
                Already Logged in...
                <button onClick={func}>Sign Out</button>
            </div>
            :
            <div class="login-container">
                <div class="login-title">
                    {
                        SignUp? "Register" : "Login"
                    }
                </div>
                <div class="login-form">
                    <form onSubmit={handleLogin}>
                            <div class="login-label">
                                <span>User Name:</span>
                                {
                                    SignUp?
                                        <span class="acc">Already Have an Account? &nbsp;
                                            <span class="login-switch" onClick={()=> setSignUp(false)}>
                                                Login
                                            </span>
                                        </span>
                                    :
                                        <span class='acc'>
                                            Need an Account? &nbsp;
                                            <span class="login-switch" onClick={()=> setSignUp(true)}>
                                                Register
                                            </span>
                                    </span>
                                }
                                
                            </div>
                            <input
                                required={SignUp && true}
                                type='text'
                                onChange={(e)=> {
                                    setPersonalDetails({
                                        ...PersonalDetails,
                                        username: e.target.value
                                    }) 
                                }}
                                placeholder="Enter Your Name">
                            </input>
                        {
                            SignUp && 
                            <>
                                <div class="login-label">
                                    <span>Email:</span>
                                </div>
                                <input type='email'
                                    autoComplete='email'
                                    onChange={(e)=> {
                                        setPersonalDetails({
                                            ...PersonalDetails, 
                                            email: e.target.value
                                        })
                                    }}
                                    placeholder='Enter Your Email'
                                    required
                                    />
                            </>
                        }
                        
                        <div class='login-label'>
                            <span>Password</span>
                        </div>

                        <div class='login-password'>
                            <input
                                type={showPassword? 'text': 'password'}
                                onChange={(e)=> {
                                    setPersonalDetails(
                                            {
                                                ...PersonalDetails,
                                                password: e.target.value
                                            }
                                        )
                                }}
                                placeholder='Enter your Password'
                                required
                            />
                            <div class="login-eye" onMouseDown={()=> {setShowPassword(true)}}
                                                    onMouseUp={()=> {setShowPassword(false)}}>
                                {
                                    showPassword ?
                                    <VscEyeClosed />
                                    :
                                    <VscEye />
                                }
                            </div>
                        </div>

                        <div class="login-submit">
                                <button type={'submit'}>
                                    {
                                        SignUp?
                                            'Sign Up'
                                            :
                                            'Log in'
                                    }
                                </button>
                        </div>
                    </form>

                </div>
        </div>
        }
    </div>
  )
}

export default Auth