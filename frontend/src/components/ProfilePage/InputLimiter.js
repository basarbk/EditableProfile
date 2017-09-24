import React, { Component } from 'react';
import './InputLimiter.css';

class InputLimiter extends Component {
    render() {
        const current = this.props.content ? this.props.content.length : 0;
        const rate = current / this.props.limit;
        let className = ''
        if(rate < 0.5){
            className='limit-normal';
        } else if(rate <0.75) {
            className='limit-warning';
        } else {
            className='limit-alert';
        }
        return (
            <div style={{float:'right', fontSize:'10px'}}>
                <span className={className}>{current}/{this.props.limit}</span>
            </div>
        );
    }
}

export default InputLimiter;