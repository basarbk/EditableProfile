import React, { Component } from 'react';

class UserDetails extends Component {
    render() {
        return (
            <div>
                <h3>Profile of {this.props.profile.displayName} from {this.props.profile.location.city}</h3>
                <table>
                        {Object.keys(this.props.profile).filter(k => k!=='location').map((key, index) => 
                            <tr key={index}>
                                <td><strong>{key}</strong></td>
                                <td>{this.props.profile[key]}</td>
                            </tr>
                        )}
                </table>
                
            </div>
        );
    }
}

export default UserDetails;