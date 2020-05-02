import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import Color from 'theme/palette'
import { AppBar, Toolbar, Hidden, IconButton, Typography } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import InputIcon from '@material-ui/icons/Input';

const useStyles = makeStyles(theme => ({
	root: {
		boxShadow: 'none'
	},
	flexGrow: {
		flexGrow: 1
	},
	signOutButton: {
		marginLeft: theme.spacing(1)
	},
	logo: {
		color: Color.logo.primary
	},
	toolbar: {
		background: Color.background.toolbar
	}
}));

const Topbar = props => {
	const { className, onSidebarOpen, ...rest } = props;

	const classes = useStyles();

	const [notifications] = useState([]);

	return (
		<AppBar
			{...rest}
			className={clsx(classes.root, className)}
		>
			<Toolbar className={classes.toolbar}>
				<RouterLink to="/">
					<Typography variant="h3" className={classes.logo}>
						SI-Perpustakaan
					</Typography>
				</RouterLink>
				<div className={classes.flexGrow} />
				<Hidden mdDown>
					<IconButton
						className={classes.signOutButton}
						color="inherit"
					>
						<InputIcon />
					</IconButton>
				</Hidden>
				<Hidden lgUp>
					<IconButton
						color="inherit"
						onClick={onSidebarOpen}
					>
						<MenuIcon />
					</IconButton>
				</Hidden>
			</Toolbar>
		</AppBar>
	);
};

Topbar.propTypes = {
	className: PropTypes.string,
	onSidebarOpen: PropTypes.func
};

export default Topbar;