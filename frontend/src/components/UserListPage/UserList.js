import React, { Component } from 'react';
import { Table, notification } from 'antd';
import UserDetails from './UserDetails';

import * as api from '../../api';

const notify = (type, mess, description) => {
    notification[type]({
      message: `${mess}`,
      description: `${description}`
    });
  }

class UserListsPage extends Component {

    constructor(props){
        super(props);
        this.toggleProfile = this.toggleProfile.bind(this);
        this.state = {
            profiles:[],
            loading:false,
            selectedidx: -1,
            selectedUser:null
        }
    }

    
    componentWillMount() {
        this.setState({loading: true});
        api.getProfiles().then(response => {
            this.setState({loading: false});
            this.setState({profiles:response.data});
        }).catch( err => {
            this.setState({loading: false});
            notify('error', 'Profile Load failed');
        });
    }

    toggleProfile = (idx) => {
      if(this.state.selectedidx === idx) {
        this.setState({selectedidx:-1, selectedUser: null});
      } else {
        api.getProfileOf(this.state.profiles[idx].id).then(response => {
          this.setState({selectedidx:idx, selectedUser:response.data});
        }).catch(err=> notify('error', 'Profile Load Fail', 'Failed to load profile'));

      }
    }

    columns = [{
        title: 'Display Name',
        dataIndex: 'displayName',
        key: 'displayName'
      }, {
        title: 'Gender',
        dataIndex: 'gender',
        key: 'gender',
      }, {
        title: 'Birthday',
        dataIndex: 'birthday',
        key: 'birthday',
      }, {
        title: 'Height',
        dataIndex: 'height',
        key: 'heigt',
      }, {
        title: 'Location',
        dataIndex: 'location.city',
        key: 'location.city',
      }, {
        title: 'Action',
        key: 'action',
        render: (text, record, index) => {
          return (
          <span>
            <a href="" onClick={(e) => {e.preventDefault(); this.toggleProfile(index)}}>{this.state.selectedidx === index ? 'Hide Details' : 'Show Details'}</a>
          </span>
        )},
      }];
    

    render() {
        return (
            <div>
                <h1>Profiles</h1>
                <Table columns={this.columns} dataSource={this.state.profiles} loading={this.state.loading} bordered size="middle" />
                {this.state.selectedUser && <UserDetails profile={this.state.selectedUser}/>}
            </div>
        );
    }
}

export default UserListsPage;