import React, { Component } from 'react';
import { LocaleProvider, Layout, Menu, Icon} from 'antd';
import './RootContainer.css'
import UserList from './UserListPage/UserList';
import ProfilePage from './ProfilePage/ProfilePage';
import LoginPage from './LoginPage/LoginPage';
import * as api from '../api';
import enUS from 'antd/lib/locale-provider/en_US';
const { Header, Content } = Layout;

class HomePage extends Component {

    constructor(props){
        super(props)
        this.getPageComponent = this.getPageComponent.bind(this);
        this.navigate = this.navigate.bind(this);
        this.loginSuccess = this.loginSuccess.bind(this);
        this.logoutSuccess = this.logoutSuccess.bind(this);
        this.state={
            currentPage: "1",
            loggedin: false,
            username: null,
            password: null
        }
    }

    loginSuccess = (user, pass) => {
        this.setState({loggedin:true});
    }

    logoutSuccess = () => {
        this.setState({loggedin:false});
    }
    
    getPageComponent = () => {
        if(this.state.currentPage === "1")
         return <UserList />
        
         if(this.state.loggedin)
            return <ProfilePage />
        
        return <LoginPage loginSuccess={this.loginSuccess} />
    }

    navigate = (item) => {
        if(item.key === "3"){
            api.logout();
            this.logoutSuccess();
        } else {
            this.setState({currentPage:item.key});
        }
    }

    render() {
        return (
            <LocaleProvider locale={enUS}>
            <Layout>
                <Header className="header">
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={[this.state.currentPage]}
                    selectedKeys={[this.state.currentPage]}
                    style={{ lineHeight: '64px' }}
                    onClick={this.navigate}>
                    <Menu.Item key="1">Profile List</Menu.Item>
                    <Menu.Item key="2">My Profile</Menu.Item>
                    {this.state.loggedin && <Menu.Item key="3" className="logout" style={{float:'right'}}><Icon type="arrow-right" /> Logout</Menu.Item>}
                </Menu>

                </Header>
                <div>
                    <Content className="mainContent">
                        {this.getPageComponent()}
                    </Content>
                </div>
            </Layout>
            </LocaleProvider>
        );
    }
}

export default HomePage;