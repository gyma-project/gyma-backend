-- Criação da tabela 'roles'
CREATE TABLE public.roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Criação da tabela 'images'
CREATE TABLE public.images (
    id SERIAL PRIMARY KEY,
    id_object VARCHAR(255) UNIQUE NOT NULL
);

-- Criação da tabela 'profiles'
CREATE TABLE public.profiles (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    keycloak_user_id UUID NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    image_id INTEGER,
    FOREIGN KEY (image_id) REFERENCES public.images(id)
);

-- Criação da tabela de relacionamento 'profile_roles' (muitos para muitos)
CREATE TABLE public.profile_roles (
    profile_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (profile_id, role_id),
    FOREIGN KEY (profile_id) REFERENCES public.profiles(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE
);

CREATE TABLE public.exercises (
    id SERIAL PRIMARY KEY,
    muscle_group VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    amount INTEGER NOT NULL,
    repetition INTEGER NOT NULL
);

CREATE TABLE public.training_sheets (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL,
    trainer_id INTEGER NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by INTEGER NOT NULL,
    FOREIGN KEY (student_id) REFERENCES public.profiles(id),
    FOREIGN KEY (trainer_id) REFERENCES public.profiles(id),
    FOREIGN KEY (update_by) REFERENCES public.profiles(id)
);

CREATE TABLE public.training_sheet_exercise (
    training_sheet_id INTEGER NOT NULL,
    exercise_id INTEGER NOT NULL,
    PRIMARY KEY (training_sheet_id, exercise_id),
    FOREIGN KEY (training_sheet_id) REFERENCES public.training_sheets(id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES public.exercises(id) ON DELETE CASCADE
);

CREATE TABLE public.training_times (
    id SERIAL PRIMARY KEY,
    training_day VARCHAR(255) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    students_limit INTEGER NOT NULL,
    trainer_id INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by INTEGER NOT NULL,
    FOREIGN KEY (trainer_id) REFERENCES public.profiles(id),
    FOREIGN KEY (update_by) REFERENCES public.profiles(id)
);

CREATE TABLE public.transactions (
    id SERIAL PRIMARY KEY,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    created_at DATE DEFAULT CURRENT_DATE,
    updated_at DATE DEFAULT CURRENT_DATE,
    update_by INTEGER NOT NULL,
    created_by INTEGER NOT NULL,
    category VARCHAR(255) NOT NULL,
    FOREIGN KEY (update_by) REFERENCES public.profiles(id),
    FOREIGN KEY (created_by) REFERENCES public.profiles(id)
);

CREATE TABLE public.appointments (
    id SERIAL PRIMARY KEY,
    training_time_id INTEGER NOT NULL,
    student_id INTEGER NOT NULL,
    trainer_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (training_time_id) REFERENCES public.training_times(id),
    FOREIGN KEY (student_id) REFERENCES public.profiles(id),
    FOREIGN KEY (trainer_id) REFERENCES public.profiles(id)
);

CREATE TABLE public.training_reports (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL,
    trainer_id INTEGER NOT NULL,
    height DOUBLE PRECISION,
    weight DOUBLE PRECISION,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by INTEGER NOT NULL,
    FOREIGN KEY (student_id) REFERENCES public.profiles(id),
    FOREIGN KEY (trainer_id) REFERENCES public.profiles(id),
    FOREIGN KEY (update_by) REFERENCES public.profiles(id)
);



