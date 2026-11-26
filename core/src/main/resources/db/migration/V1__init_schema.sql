CREATE TABLE competition (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         VARCHAR(50) NOT NULL UNIQUE,
    budget       NUMERIC(4,1) NOT NULL,
    max_per_club INTEGER NOT NULL
);

CREATE TABLE season (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    competition_id BIGINT NOT NULL REFERENCES competition (id),
    name           VARCHAR(50) NOT NULL,
    mode           VARCHAR(20) NOT NULL CHECK (mode IN ('SIMULATED', 'REAL')),
    UNIQUE (competition_id, name)
);

CREATE TABLE club (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    competition_id BIGINT NOT NULL REFERENCES competition (id),
    name           VARCHAR(50) NOT NULL,
    short_name     VARCHAR(10) NOT NULL,
    external_id    VARCHAR(50) UNIQUE
);

CREATE INDEX idx_club_competition ON club (competition_id);

CREATE TABLE player (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    club_id     BIGINT       NOT NULL REFERENCES club (id),
    first_name  VARCHAR(80)  NOT NULL,
    last_name   VARCHAR(80)  NOT NULL,
    position    VARCHAR(3)   NOT NULL CHECK (position IN ('GK', 'DEF', 'MID', 'FWD')),
    price       NUMERIC(4,1) NOT NULL CHECK (price > 0),
    rating      INTEGER      NOT NULL CHECK (rating BETWEEN 1 AND 100),
    external_id VARCHAR(50)  UNIQUE
);

CREATE INDEX idx_player_club ON player (club_id);

CREATE TABLE gameweek (
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    season_id BIGINT      NOT NULL REFERENCES season (id),
    number    INTEGER     NOT NULL CHECK (number > 0),
    deadline  TIMESTAMPTZ NOT NULL,
    status    VARCHAR(10) NOT NULL CHECK (status IN ('OPEN', 'LOCKED', 'FINISHED')),
    UNIQUE (season_id, number)
);

CREATE TABLE match (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    gameweek_id  BIGINT      NOT NULL REFERENCES gameweek (id),
    home_club_id BIGINT      NOT NULL REFERENCES club (id),
    away_club_id BIGINT      NOT NULL REFERENCES club (id),
    kickoff      TIMESTAMPTZ NOT NULL,
    status       VARCHAR(10) NOT NULL CHECK (status IN ('SCHEDULED', 'FINISHED')),
    home_goals   INTEGER CHECK (home_goals >= 0),
    away_goals   INTEGER CHECK (away_goals >= 0),
    CHECK (home_club_id <> away_club_id)
);

CREATE INDEX idx_match_gameweek ON match (gameweek_id);
CREATE INDEX idx_match_home_club ON match (home_club_id);
CREATE INDEX idx_match_away_club ON match (away_club_id);

CREATE TABLE app_user (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username      VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL
);

CREATE TABLE fantasy_team (
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id   BIGINT       NOT NULL REFERENCES app_user (id),
    season_id BIGINT       NOT NULL REFERENCES season (id),
    name      VARCHAR(100) NOT NULL,
    version   BIGINT       NOT NULL DEFAULT 0,
    UNIQUE (user_id, season_id)
);

CREATE INDEX idx_fantasy_team_season ON fantasy_team (season_id);

CREATE TABLE fantasy_team_player (
    fantasy_team_id BIGINT  NOT NULL REFERENCES fantasy_team (id),
    player_id       BIGINT  NOT NULL REFERENCES player (id),
    starter         BOOLEAN NOT NULL,
    PRIMARY KEY (fantasy_team_id, player_id)
);

CREATE INDEX idx_fantasy_team_player_player ON fantasy_team_player (player_id);

CREATE TABLE gameweek_lineup (
    gameweek_id     BIGINT  NOT NULL REFERENCES gameweek (id),
    fantasy_team_id BIGINT  NOT NULL REFERENCES fantasy_team (id),
    player_id       BIGINT  NOT NULL REFERENCES player (id),
    starter         BOOLEAN NOT NULL,
    PRIMARY KEY (gameweek_id, fantasy_team_id, player_id)
);

CREATE INDEX idx_gameweek_lineup_team ON gameweek_lineup (fantasy_team_id);
CREATE INDEX idx_gameweek_lineup_player ON gameweek_lineup (player_id);
