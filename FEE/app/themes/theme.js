import { createMuiTheme } from '@material-ui/core/styles';

export default createMuiTheme({
    palette: {
        common: {
            black: '#000',
            white: '#fff'
        },
        background: {
            paper: '#fff',
            default: '#fafafa'
        },
        primary: {
            light: 'rgba(56, 149, 207, 1)',
            main: 'rgba(53, 153, 226, 1)',
            dark: 'rgba(0, 149, 255, 1)',
            contrastText: '#fff'
        },
        secondary: {
            light: 'rgba(160, 152, 154, 1)',
            main: 'rgba(113, 109, 116, 1)',
            dark: 'rgba(44, 43, 43, 1)',
            contrastText: '#fff'
        },
        error: {
            light: '#e57373',
            main: '#f44336',
            dark: '#d32f2f',
            contrastText: '#fff'
        },
        text: {
            primary: 'rgba(0, 0, 0, 0.87)',
            secondary: 'rgba(0, 0, 0, 0.54)',
            disabled: 'rgba(0, 0, 0, 0.38)',
            hint: 'rgba(0, 0, 0, 0.38)'
        }
    }
});